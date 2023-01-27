const EREX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const PREX = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
const NREX = /^\d+$/;

export function emailValidator(email){
    return email.match(EREX) ? true : false;
}

export function pwValidator(password){
    return password.match(PREX) ? true : false;
}

export function numValidator(number){
    return number.match(NREX) ? true : false;
}