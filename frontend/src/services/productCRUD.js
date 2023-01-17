const getAllProducts = async () => {
    return await fetch(`${process.env.API_URL}/inventory/all`, {
      credentials: 'include',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  const deleteProduct = async (id) => {
    await fetch(`${process.env.API_URL}/inventory/delete/${id}`, {
      credentials: 'include',
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => response.json());
  }

  const updateProduct = async (product, id) => {
    return await fetch(`${process.env.API_URL}/inventory/update/${id}`, {
      credentials: 'include',
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(product)
    })
  }

  const updatePhoto = async (photo, id) => {
    let formData = new FormData();
    formData.append("photo",photo);
    return await fetch(`${process.env.API_URL}/inventory/update/${id}/photo`, {
      credentials: 'include',
      method: 'PUT',
      body: formData
    })
  }

  const addProduct = async (product,photo) => {
    let formData = new FormData();
    formData.append("name",product.name);
    formData.append("stock",product.stock);
    formData.append("description",product.description);
    formData.append("price",product.price);
    formData.append("photo",photo);
    return await fetch(`${process.env.API_URL}/inventory/add`, {
      credentials: 'include',
      method: 'POST',
      body: formData
    })
  }

  export {getAllProducts,deleteProduct,updateProduct, updatePhoto, addProduct};