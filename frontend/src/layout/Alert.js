import React from 'react'

function Alert({message}) {
  return (
    <div className="col-lg-7 alert alert-danger mt-3" role="alert">{message}</div>
  )
}

export default Alert;