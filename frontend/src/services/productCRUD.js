const getAllProducts = async () => {
    return await fetch("http://localhost:8080/api/inventory/all", {
      credentials: 'include',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  const deleteProduct = async (id) => {
    await fetch(`http://localhost:8080/api/inventory/delete/${id}`, {
      credentials: 'include',
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => response.json());
  }

  const updateProduct = async (product, id) => {
    return await fetch(`http://localhost:8080/api/inventory/update/${id}`, {
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
    return await fetch(`http://localhost:8080/api/inventory/update/${id}/photo`, {
      credentials: 'include',
      method: 'PUT',
      body: formData
    })
  }

  export {getAllProducts,deleteProduct,updateProduct, updatePhoto};