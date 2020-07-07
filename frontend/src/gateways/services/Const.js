export const HOST = process.env.REACT_APP_API_HOST;
const API = HOST + "/api"
export const V1 = API + "/v1"
export const PIC_PATH = HOST+"/pics"
export const FILE_PATH = HOST+"/files"
export const FILE_ATTACH_PATH = HOST+"/not-secure/api/v1/file"

export function PIC(picId) {
    return PIC_PATH + "/" + picId
}

export function FILE(id) {
    return FILE_PATH + "/" + id
}

export function FILE_ATTACH(id) {
    return FILE_ATTACH_PATH + "/" + id
}

console.log('CONST: start')
console.log(HOST)
console.log('CONST: end')