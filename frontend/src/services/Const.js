const HOST = process.env.REACT_APP_API_HOST;
const API = HOST + "/api"
export const V1 = API + "/v1"
export const PIC_PATH = HOST+"/pics"
export function PIC(picId) {
    return PIC_PATH + "/" + picId
}

console.log('CONST: start')
console.log(HOST)
console.log('CONST: end')