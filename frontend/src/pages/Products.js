import React,{ useContext, useEffect, useState} from 'react';
import { UserContext } from '../App';

export default function Products() {
  const [products,setProducts] = useState([]);
  const role = useContext(UserContext);
  const [counter,setCounter] = useState(1);

  useEffect(() => {
    getAllProducts();
  },[])

   const getAllProducts = async () => {
     await fetch("http://localhost:8080/api/inventory/all", {
            credentials: 'include',
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
        .then(data => setProducts(data)
        );
  }
  
  return (
    <div className="container-fluid">
      <div className="row justify-content-around">
        <h1>Inventory List</h1>
        {products.map((product, index) => (
            <div className="row p-2 bg-white border rounded my-3" style={{width:800+'px'}} key={index}>
            <div className="col-md-3 mt-1"><img className="img-fluid img-responsive rounded product-image" style={{height:125+'px'}} src={`data:image/jpeg;base64,`+product.photo} alt={product.name}></img></div>
            <div className="col-md-6 mt-1">
                <h5>{product.name}</h5>
                <div className="d-flex flex-row">
                      <span>#{index+1}</span>
                </div>
                <p className="text-justify text-truncate para mb-0">{product.description}<br></br><br></br></p>
            </div>
            <div className="align-items-center align-content-center col-md-3 border-left mt-1">
                <div className="d-flex flex-row justify-content-around">
                    <h4 className="mr-1">$ {product.price}</h4>
                </div>
                <span>{product.stock} available</span>
                <div className="d-flex flex-column mt-2">
                  {role.role === "ROLE_ADMIN" ? 
                  <div className="d-flex flex-column">
                    <button className="btn btn-outline-info btn-sm mt-2" type="button" data-bs-toggle="modal" data-bs-target="#updateProd">Update product</button>
                    <div className="modal fade" id="updateProd" tabIndex="-1" aria-labelledby="prodUp" aria-hidden="true">
                      <div className="modal-dialog">
                        <div className="modal-content">
                          <div className="modal-header">
                            <h1 className="modal-title fs-5" id="prodUp">{product.name}</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div className="modal-body">
                          <form className="row justify-content-around">
                            <div className="col-lg-7 form-outline mb-2">
                                <label className="form-label" htmlFor="form2Example1">Price</label>
                                <input type="number" name="price" id="form2Example1" className="form-control" defaultValue={product.price}/>
                            </div>
                            <div className="form-outline mb-4 col-lg-7">
                                <label className="form-label" htmlFor="form2Example2">Description</label>
                                <input type="text" name="description" id="form2Example2" className="form-control" defaultValue={product.description}/>
                            </div>
                            <div className="form-outline mb-4 col-lg-7">
                                <label className="form-label" htmlFor="form2Example3">Stock</label>
                                <input type="number" name="stock" id="form2Example3" className="form-control" defaultValue={product.stock}/>
                            </div>
                        </form>
                          </div>
                          <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" className="btn btn-primary">Update</button>
                          </div>
                        </div>
                      </div>
                    </div>
                    <button className="btn btn-outline-danger btn-sm mt-2" type="button" data-bs-toggle="modal" data-bs-target="#deleteProd">Delete product</button>
                    <div className="modal fade" id="deleteProd" tabIndex="-1" aria-labelledby="prodDel" aria-hidden="true">
                      <div className="modal-dialog">
                        <div className="modal-content">
                          <div className="modal-header">
                            <h1 className="modal-title fs-5" id="prodDel">Do you want to delete {product.name}</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div className="modal-footer justify-content-center">
                          <button type="button" className="btn btn-danger" data-bs-dismiss="modal">Yes</button>
                            <button type="button" className="btn btn-primary" data-bs-dismiss="modal">No</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                   :
                   <div className="d-flex flex-column">
                    <button className="btn btn-primary" type="button">
                    Buy product
                    </button>
                    <div className="col-12 mt-2">
                            <div className="input-group">
                                <input type="number" id="qty_input" className="form-control form-control-sm text-center mx-1" defaultValue="1" min="1" max={product.stock}></input>
                            </div>
                        </div>
                  </div>}
                </div>
            </div>
          </div>
          )
        )}
      </div>
    </div>
  )
}
