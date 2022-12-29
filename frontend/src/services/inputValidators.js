const EREX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const PREX = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

export function emailValidator(email){
    if(email.match(EREX))return true;    
    return false;
}

export function pwValidator(password){
    if(password.match(PREX))return true;    
    return false;
}