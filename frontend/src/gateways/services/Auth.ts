import * as CONST from './Const'
import API from "./Api";

export default {

    forgotPasswordChange(password: string, token: string): Promise<any> {
        const urlRequest = `${CONST.V1}/auth/forgot-password/change`
        const method = 'POST'

        let contentJson = {
            password,
            token
        };

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    forgotPasswordStart(login: string): Promise<any> {
        const urlRequest = `${CONST.V1}/auth/forgot-password/start`
        const method = 'POST'

        let contentJson =            {
                login
            }        ;

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    login(email: string, password: string) {
        const urlRequest = `${CONST.V1}/auth/login`
        const method = 'POST'

        let contentJson =            {
                email,
                password
            }        ;

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    logout() {
        const urlRequest = `${CONST.V1}/auth/logout`
        const method = 'POST'

        let contentJson =            {}        ;

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    refresh(refreshToken: string) {
        const urlRequest = `${CONST.V1}/auth/refresh`
        const method = 'POST'

        let contentJson =            {
                refreshToken
            }        ;

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    signUp(email: string, login: string, password: string) {
        const urlRequest = `${CONST.V1}/auth/sign-up`
        const method = 'POST'

        let contentJson =            {
                email,
                login,
                password
            }        ;

        let response = API.api(urlRequest, {
            method: method,
            contentJson:contentJson
        });

        return response;
    },


}