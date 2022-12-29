import React,{ useEffect, useState} from 'react';

export default function Products() {
  const [products,setProducts] = useState([]);
  const [photos,setPhotos] = useState([]);

  useEffect(() => {
    getAllProducts();
  },[])

   const getAllProducts = async () => {
    const response = await fetch("http://localhost:8080/api/inventory/all", {
            credentials: 'include',
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
        .then(data => {
          setProducts(data);
        });
  }
  
  return (
    <div className="container-fluid">
      <div className="row justify-content-around">
      <h1>Inventory List</h1>
      {
        products.map((product, index) => (
          <div className="card" style={{width: 18 + 'em'}}key={index}>
            {product.photos.map((photos, index) => <img key={index} className="card-img-top" alt={photos.name}></img>)}
    
            <div className="card-body">
              <h5 className="card-title">{product.name}</h5>
              <p className="card-text">{product.description}</p>
              <p className="card-text">$ {product.price}</p>
              <p className="card-text">{product.stock} available</p>
              <a href="#" className="btn btn-primary"></a>
            </div>
          </div> 
        ))
      }
      </div>
  </div>
  )
}
