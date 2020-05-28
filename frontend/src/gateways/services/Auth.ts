import * as CONST from './Const'
import TokenRepository from "./TokenRepository";

export default {

    forgotPasswordChange(password: string, token: string) {
        const urlRequest = `${CONST.V1}/auth/forgot-password/change`
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }

        let contentJson =
            {
                password,
                token
            }
        ;

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    forgotPasswordStart(login: string) {
        const urlRequest = `${CONST.V1}/auth/forgot-password/start`
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }

        let contentJson =
            {
                login
            }
        ;

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    login(email: string, password: string) {
        const urlRequest = `${CONST.V1}/auth/login`
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }

        let contentJson =
            {
                email,
                password
            }
        ;

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    logout() {
        const urlRequest = `${CONST.V1}/auth/logout`
        const method = 'POST'

        let headers = {
            'X-Auth-Token': TokenRepository.getAccessToken()!,
            'Content-Type': 'application/json;charset=utf-8'
        }

        let contentJson =
            {}
        ;

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    refresh(refreshToken: string) {
        const urlRequest = `${CONST.V1}/auth/refresh`
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }

        let contentJson =
            {
                refreshToken
            }
        ;

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    signUp(email: string, login: string, password: string) {
        const urlRequest = `${CONST.V1}/auth/sign-up`
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }

        let contentJson =
            {
                email,
                login,
                password
            }
        ;

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },


}