package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import DAO.addItemsDAO;
import Entity.categoriesEntity;
import Entity.productsEntity;
import Entity.subcategoriesEntity;

@WebServlet("/addtemsController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50   // 50MB
)
public class addtemsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private addItemsDAO addItemsDao;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource datasource;

    @Override
    public void init() {
        addItemsDao = new addItemsDAO(datasource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thông tin cơ bản
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("category"));
        int subcategoryId = Integer.parseInt(request.getParameter("subCategory"));
        boolean bestseller = request.getParameter("bestseller") != null;

        String productId = UUID.randomUUID().toString();
        long date = System.currentTimeMillis();

        // Xử lý ảnh
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        List<String> imageUrls = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getName().startsWith("image") && part.getSize() > 0) {
                String fileName = UUID.randomUUID().toString() + "_" + extractFileName(part);
                String filePath = uploadPath + File.separator + fileName;
                part.write(filePath);
                imageUrls.add("uploads/" + fileName); // Lưu đường dẫn ảnh
            }
        }

        // Nếu không có ảnh nào, sử dụng ảnh mặc định
        String imageUrl = imageUrls.isEmpty() ? "placeholder.jpg" : String.join(";", imageUrls);

        // Tạo entity
        categoriesEntity category = new categoriesEntity();
        category.setCategoryId(categoryId);

        subcategoriesEntity subCategory = new subcategoriesEntity();
        subCategory.setSubcategoryId(subcategoryId);

        productsEntity product = new productsEntity();
        product.setProductId(productId);
        product.setName(name);
        product.setImageUrl(imageUrl);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setSubCategory(subCategory);
        product.setBestseller(bestseller);
        product.setDate(date);

        // Xử lý kích cỡ sản phẩm
        String[] sizes = request.getParameterValues("sizes");
        List<Integer> sizeIds = new ArrayList<>();
        if (sizes != null) {
            for (String size : sizes) {
                sizeIds.add(Integer.parseInt(size));
            }
        }

        // Lưu sản phẩm vào database
        boolean success = addItemsDao.addItems(product, sizeIds);

        if (success) {
            response.sendRedirect("/success.jsp");
        } else {
            response.sendRedirect("/error.jsp");
        }
    }

    // Helper method to extract file name from HTTP header content-disposition
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
}
