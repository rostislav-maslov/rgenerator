import * as CONST from './Const'

export default {

    create(phoneNumber, code) {
        const urlRequest = CONST.V1 + "/templateResult"
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }

        let contentJson = {phoneNumber: phoneNumber, code: code};

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

}