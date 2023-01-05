const EREX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const PREX = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

export function emailValidator(email){
    return email.match(PREX) ? true : false;
}

export function pwValidator(password){
    return password.match(PREX) ? true : false;
}