import React from 'react'

export default function Home() {
  
  return (
    <div className="container-fluid">
        <div className="row py-3 justify-content-md-center">
            <div className="col">
                <h1 className="text-center">REST API and Responsive Page</h1>
                <p>This is my first project where i implement a 
                REST API utilizing Java and Spring, it contains USER Login/Registration, 
                and Inventory endpoints for visualizing product data.</p>
                <br></br>
                <ul className="list-group">
                    <li className="list-group-item">Java</li>
                    <li className="list-group-item">Spring Security for protecting resources and Data Manipulation</li>
                    <li className="list-group-item">Spring JPA and MYSQL for persisting and storing data</li>
                    <li className="list-group-item">REACT</li>
                </ul>
            </div>
        </div>
    </div>
  )
};
