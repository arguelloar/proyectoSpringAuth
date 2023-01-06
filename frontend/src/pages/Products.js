import React, { useContext, useEffect, useState, useRef} from 'react';
import { UserContext } from '../App';
import { useNavigate } from "react-router-dom";
import { getAllProducts, deleteProduct, updateProduct, updatePhoto } from '../services/productCRUD';
import ProductAdd from './ProductAdd';

export default function Products() {
  const [products, setProducts] = useState([]);
  const role = useContext(UserContext);
  const [edit,setEdit] = useState({});
  const [photo,setPhoto] = useState('');
  const [img,setImg] = useState();
  const [open, setOpen] = useState(false);
  const [sort, setSort] = useState("ASC");

  const sortingName = (col) => {
    if(sort === "ASC"){
      const sorted = [...products].sort((a,b) =>
        a[col].toLowerCase() > b[col].toLowerCase() ? 1 : -1);
    setProducts(sorted);
    setSort("DSC")
    }
    if(sort === "DSC"){
      const sorted = [...products].sort((a,b) =>
        a[col].toLowerCase() < b[col].toLowerCase() ? 1 : -1);
    setProducts(sorted);
    setSort("ASC")
    }
  };

  const sortingNum = (col) => {
    if(sort === "ASC"){
      const sorted = [...products].sort((a,b) =>
        a[col] > b[col] ? 1 : -1);
    setProducts(sorted);
    setSort("DSC")
    }
    if(sort === "DSC"){
      const sorted = [...products].sort((a,b) =>
        a[col] < b[col] ? 1 : -1);
    setProducts(sorted);
    setSort("ASC")
    }
    
  }

  useEffect(() => {
    getAllProducts().then(response => response.json())
      .then(data => setProducts(data));
  }, []);

  const handleChange = (e) => {
    setEdit({...edit,[e.target.name]: e.target.value});
  }


  const onClick = (e,id) => {
    e.preventDefault();
    updatePhoto(photo,id);
    updateProduct(edit,id).then(response => response.ok ? window.location.reload() : alert("Bad request"))  
  }

  const onPhotoChange = (e) => {
    setPhoto(e.target.files[0]);
    setImg(URL.createObjectURL(e.target.files[0]));
  }

    return (
      <div className="container-fluid">
        <h1>Inventory List</h1>
        {role.role === "ROLE_ADMIN" ? <button className="col-2 btn btn-outline-primary mt-3"
          onClick={() => setOpen(!open)}
          aria-controls="example-collapse-text"
          aria-expanded={open}>
          Add new product
        </button> : <i></i>}
        
        {open && <ProductAdd />}
        
        
        <div className="row justify-content-around">
          <table className="table border shadow mt-3">
            <thead>
              <tr>
                <th scope="col">#ID</th>
                <th scope="col" className="user-select-none" onClick={() => sortingName("name")}><a role="button">NAME</a></th>
                <th scope="col" className="user-select-none" onClick={() => sortingNum("price")}><a role="button">PRICE</a></th>
                <th scope="col" className="user-select-none" onClick={() => sortingNum("stock")}><a role="button">STOCK</a></th>
                <th scope="col">ACTIONS</th>
              </tr>
            </thead>
            <tbody>
              {products.map((product, index) => (
                <tr key={index}>
                  <th scope="row">
                    {index + 1}
                  </th>
                  <td>{product.name}</td>
                  <td>{product.price}</td>
                  <td>{product.stock}</td>
                  <td>
                    <button className="btn btn-outline-info mx-2" data-bs-toggle="modal" data-bs-target={"#viewProduct" + product.id}>View</button>
                    {role.role === "ROLE_ADMIN" ? <button data-bs-toggle="modal" data-bs-target={"#editProduct"+product.id} className="btn btn-outline-warning mx-2" onClick={(e) => {
                      e.preventDefault();
                      setEdit(product);
                      setImg(`data:image/jpeg;base64,`+product.photo);
                    }}
                      >Edit</button> : <i></i>}
  
                    {role.role === "ROLE_ADMIN" ? <button className="btn btn-outline-danger mx-2" onClick={() => {
                      deleteProduct(product.id);
                      window.location.reload();
                    }}>Delete</button> : <i></i>}
                    <div className="modal fade" id={"viewProduct" + product.id} tabIndex="-1" aria-labelledby="viewProductLabel" aria-hidden="true">
                      <div className="modal-dialog">
                        <div className="modal-content">
                          <div className="modal-header">
                            <h1 className="modal-title text-center fs-5" id="viewProductLabel">#{index + 1}</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div className="modal-body">
                            <div className="d-flex justify-content-center container">
                              <div className="card p-3 bg-white"><i className="fa fa-apple"></i>
                                <div className="about-product text-center"><img src={`data:image/jpeg;base64,` + product.photo} width={200 + 'px'}></img>
                                  <div>
                                    <h4>{product.name}</h4>
                                    <h6 className="mt-0 text-black-50">{product.description}</h6>
                                  </div>
                                </div>
                                <div className="stats mt-2">
                                  <div className="d-flex justify-content-between p-price"><span>Price</span><span>${product.price}</span></div>
                                  <div className="d-flex justify-content-between p-price"><span>Available</span><span>{product.stock}</span></div>
                                </div>
                                <div className="d-flex justify-content-between total font-weight-bold mt-4"><span>Total in product</span><span>${(product.price * product.stock).toFixed(2)}</span></div>
                              </div>
                            </div>
                          </div>
                          <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="modal fade" id={"editProduct"+product.id} tabIndex="-1" aria-labelledby="editProductLabel" aria-hidden="true">
                      <div className="modal-dialog">
                        <div className="modal-content">
                          <div className="modal-header">
                            <h1 className="modal-title text-center fs-5" id="editProductLabel">#{index + 1}</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div className="modal-body">
                            <form className="row justify-content-around" key={product.id}>
                            <div className="col-lg-7 form-outline mb-2">
                                <label className="form-label" htmlFor="form0"></label>     
                                <img src={img} id="editImage" width={200 + 'px'}/>                     
                                <input type="file" name="file" id="form0" className="form-control mt-2" accept="image/*" onChange={(e) => onPhotoChange(e,product.id)}/>
                              </div>
                              <div className="col-lg-7 form-outline mb-2">
                                <label className="form-label" htmlFor="form1">Name</label>
                                <input type="text" name="name" id="form1" className="form-control" defaultValue={product.name} onChange={(e) => handleChange(e)} />
                              </div>
                              <div className="col-lg-7 form-outline mb-2">
                                <label className="form-label" htmlFor="form2">Price</label>
                                <input type="number" step="0.01" name="price" id="form2" className="form-control" defaultValue={product.price} onChange={(e) => handleChange(e)} />
                              </div>
                              <div className="form-outline mb-4 col-lg-7">
                                <label className="form-label" htmlFor="form3">Description</label>
                                <input type="text" name="description" id="form3" className="form-control" defaultValue={product.description} onChange={(e) => handleChange(e)}/>
                              </div>
                              <div className="form-outline mb-4 col-lg-7">
                                <label className="form-label" htmlFor="form4">Stock</label>
                                <input type="number" step="0.01" name="stock" id="form4" className="form-control" defaultValue={product.stock} onChange={(e) => handleChange(e)}/>
                              </div>
                              <div className="modal-footer">
                            <button type="submit" className="btn btn-primary btn-block" data-bs-dismiss="modal" onClick={(e) => onClick(e,product.id)}>Update</button>
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                          </div>
                            </form>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    )
}


