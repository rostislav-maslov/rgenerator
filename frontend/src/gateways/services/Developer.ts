import * as CONST from './Const'
import API from './Api'

export default {

    me(): Promise<any> {
        const urlRequest = `${CONST.V1}/user/me`
        const method = 'GET'

        let response = API.api(urlRequest,  {
            method: method
        });

        return response;
    },

    connectGithub(code:string): Promise<any>  {
        const urlRequest = `${CONST.V1}/user/github`
        const method = 'POST'

        let contentJson = {code};

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },


}