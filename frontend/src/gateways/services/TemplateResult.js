import * as CONST from './Const'
import TokenRepository from "./TokenRepository";

export default {
    create(generateId, content) {
        const urlRequest = CONST.V1 + "/template-result"
        const method = 'POST'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
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