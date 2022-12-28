export default function cookieCheck(){
    let cookie = document.cookie;
    if(cookie)return true;
    return false; 
}
