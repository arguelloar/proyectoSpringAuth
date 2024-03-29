import React, {useState} from 'react';
import {addProduct} from "../services/productCRUD";
import Alert from '../layout/Alert';

export default function ProductAdd({setOpen,setRefresh,refresh}) {
  const [product, setProduct] = useState({});
  const [img,setImg] = useState("https://www.kindpng.com/picc/m/564-5640631_file-antu-insert-image-svg-insert-image-here.png");
  const [alertShow,setAlertShow] = useState(false);
  const [message] = useState("Bad product format, make sure you fill all the fields");


  const onInputChange = (e) => {
    setProduct({ ...product, [e.target.name]: e.target.value });
  };

  function handleOpenWidget(e){
    e.preventDefault();
    let myWidget = window.cloudinary.createUploadWidget({
      cloudName: 'domamliq5', 
      uploadPreset: 'xsyz6lvv'}, (error, result) => { 
        if (!error && result && result.event === "success") { 
          setImg(result.info.url);
        }
      }
    );
    myWidget.open();
  }

  const onSubmit = (e) => {
    e.preventDefault();
    addProduct(product,img).then(res => {
      if(res.ok){
        setRefresh(!refresh);
        setOpen(false);
      }else{ 
        setAlertShow(true);
      }
    })}


  return (
    <div className="mx-2 my-2 border">
      <form className="row justify-content-around" onSubmit={(e) => onSubmit(e)}>
        {alertShow && <Alert message={message}/>}
        <div className="col-lg-7 form-outline mb-2">
        <p>.jpg Images Only</p>
          <label className="form-label" htmlFor="form0"></label>
          <img src={img} id="editImage" width={200 + 'px'} /><br/>
          <button id="upload_widget" class="cloudinary-button mt-2" onClick={(e) => handleOpenWidget(e)}>Upload</button> 
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
