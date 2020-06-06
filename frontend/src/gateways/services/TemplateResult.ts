import * as CONST from './Const'
import Api from "./Api";

export default {
    create(generateId: string, content: string): Promise<any> {
        const urlRequest = CONST.V1 + "/template-result"
        const method = 'POST'
        let contentJson = {generateId: generateId, content: content};

        return Api.api(urlRequest, {
            method: method,
            contentJson:contentJson
        });
    },
}