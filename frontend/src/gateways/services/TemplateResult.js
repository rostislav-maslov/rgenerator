import * as CONST from './Const'

export default {
    create(generateId, content) {
        const urlRequest = CONST.V1 + "/template-result"
        const method = 'POST'

        let headers = {
             // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }
        let contentJson = {generateId: generateId, content: content};

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });
        return response;
    },
}