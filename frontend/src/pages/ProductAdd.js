import React, {useState} from 'react';
import {addProduct} from "../services/productCRUD";
import Alert from '../layout/Alert';
import { useNavigate } from "react-router-dom";

export default function ProductAdd({setOpen}) {
  const navigate = useNavigate();
  const [product, setProduct] = useState({});
  const [photo, setPhoto] = useState('');
  const [img,setImg] = useState("https://www.kindpng.com/picc/m/564-5640631_file-antu-insert-image-svg-insert-image-here.png");
  const [alertShow,setAlertShow] = useState(false);
  const [message] = useState("Bad product format, make sure you fill all the fields");


  const onInputChange = (e) => {
    setProduct({ ...product, [e.target.name]: e.target.value });
  };

  const onPhotoChange = (e) => {
    setPhoto(e.target.files[0]);
  }

  const onSubmit = (e) => {
    e.preventDefault();
    addProduct(product,photo).then(res => {
      if(res.ok){
        navigate("/products")
      }else{ 
        setAlertShow(true);
      }
    })}


  return (
    <div className="mx-2 my-2 border">
      <form className="row justify-content-around" onSubmit={(e) => onSubmit(e)}>
        {alertShow && <Alert message={message}/>}
        <div className="col-lg-7 form-outline mb-2">
          <label className="form-label" htmlFor="form0"></label>
          <img src={img} id="editImage" width={200 + 'px'} />
          <input type="file" name="file" id="form0" className="form-control mt-2" accept="image/*" 
          onChange={(e) => {
            onPhotoChange(e)
            setImg(URL.createObjectURL(e.target.files[0]))
          }}/>
        </div>
        <div className="col-lg-7 form-outline mb-2">
          <label className="form-label" htmlFor="form1">Name</label>
          <input type="text" name="name" id="form1" className="form-control" onChange={(e) => onInputChange(e)} />
        </div>
        <div className="col-lg-7 form-outline mb-2">
          <label className="form-label" htmlFor="form2">Price</label>
          <input type="number" step="0.01" name="price" id="form2" className="form-control" onChange={(e) => onInputChange(e)} />
        </div>
        <div className="form-outline mb-4 col-lg-7">
          <label className="form-label" htmlFor="form3">Description</label>
          <input type="text" name="description" id="form3" className="form-control" onChange={(e) => onInputChange(e)} />
        </div>
        <div className="form-outline mb-4 col-lg-7">
          <label className="form-label" htmlFor="form4">Stock</label>
          <input type="number" step="0.01" name="stock" id="form4" className="form-control" onChange={(e) => onInputChange(e)} />
        </div>
        <div className="form-outline mb-4 col-lg-7">
        <button type="submit" className="btn btn-primary btn-block mx-2">Add new product</button>
        <button type="button" className="btn btn-secondary mx-2" onClick={() => setOpen(false)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}
