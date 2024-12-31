<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/addtemsController" method="post" enctype="multipart/form-data">
        <div>
            <p>Update Image</p>
            <div>
                <label for="image1">
                    <img src="placeholder.jpg" alt="" id="previewImage1">
                    <input type="file" id="image1" name="image1"/>
                </label>
                <label for="image2">
                    <img src="placeholder.jpg" alt="" id="previewImage2">
                    <input type="file" id="image2" name="image2"/>
                </label>
                <label for="image3">
                    <img src="placeholder.jpg" alt="" id="previewImage3">
                    <input type="file" id="image3" name="image3"/>
                </label>
                <label for="image4">
                    <img src="placeholder.jpg" alt="" id="previewImage4">
                    <input type="file" id="image4" name="image4"/>
                </label>
            </div>
        </div>

        <div>
            <p>Product Name</p>
            <input type="text" name="name" placeholder="Type Here" required />
        </div>

        <div>
            <p>Product Description</p>
            <textarea name="description" placeholder="Write content here"></textarea>
        </div>

        <div>
            <div>
                <p>Product Category</p>
                <select name="category">
                    <option value="1">Men</option>
                    <option value="2">Women</option>
                    <option value="3">Kid</option>
                </select>
            </div>
            <div>
                <p>Sub Category</p>
                <select name="subCategory">
                    <option value="1">Topwear</option>
                    <option value="2">Bottomwear</option>
                    <option value="3">Winterwear</option>
                </select>
            </div>
            <div>
                <p>Product Price</p>
                <input type="number" name="price" placeholder="25" />
            </div>
        </div>

        <div>
            <p>Product Sizes</p>
            <div>
                <label><input type="checkbox" name="sizes" value="1"> S</label>
                <label><input type="checkbox" name="sizes" value="2"> M</label>
                <label><input type="checkbox" name="sizes" value="3"> L</label>
            </div>
        </div>

        <div>
            <input type="checkbox" name="bestseller" id="bestseller">
            <label for="bestseller">Add to bestseller</label>
        </div>

        <button type="submit">ADD</button>
    </form>
</body>
</html>
