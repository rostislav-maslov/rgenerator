import * as CONST from './Const'
import API from "./Api";

export default {

    forgotPasswordChange(email: string, newPassword: string, code: string): Promise<any> {
        const urlRequest = `${CONST.V1}/auth/forgot-password/change`
        const method = 'POST'

        let contentJson = {
            email,
            newPassword,
            code
        };

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    forgotPasswordStart(email: string): Promise<any> {
        const urlRequest = `${CONST.V1}/auth/forgot-password/start`
        const method = 'POST'

        let contentJson = {
            email
        };

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    login(email: string, password: string) {
        const urlRequest = `${CONST.V1}/auth/login`
        const method = 'POST'

        let contentJson = {
            email,
            password
        };

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    logout() {
        const urlRequest = `${CONST.V1}/auth/logout`
        const method = 'POST'

        let contentJson = {};

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    refresh(refreshToken: string) {
        const urlRequest = `${CONST.V1}/auth/refresh`
        const method = 'POST'

        let contentJson = {
            refreshToken
        };

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },

    signUp(email: string, login: string, password: string) {
        const urlRequest = `${CONST.V1}/auth/sign-up`
        const method = 'POST'

        let contentJson = {
            email,
            login,
            password
        };

        let response = API.api(urlRequest, {
            method: method,
            contentJson: contentJson
        });

        return response;
    },


}