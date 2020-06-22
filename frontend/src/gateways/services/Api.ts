import * as CONST from './Const'
import TokenRepository from './TokenRepository'


// Your component own properties
type PropsType = {
    method: string,
    headers?: any,
    contentJson?: any
    body?: any
}


async function updateAccessToken(): Promise<boolean> {
    const method = 'POST'
    const urlRequest = `${CONST.V1}/auth/refresh`

    let headers: any = {
        'Content-Type': 'application/json;charset=utf-8'
    }

    let init: any = {
        method: method,
        headers: headers,
        body: JSON.stringify({
            refreshToken: TokenRepository.getRefreshToken()
        })
    }

    debugger

    let fetchRequest = fetch(urlRequest, init);
    let responseFetch: any = await fetchRequest.then();
    if (responseFetch.ok == false) {
        TokenRepository.clean()
        return false;
    }

    let jsonResult: any = await responseFetch.json().then()
    const accessToken = jsonResult.accessToken
    const refreshTokenRes = jsonResult.refreshToken

    TokenRepository.setRefreshToken(refreshTokenRes)
    TokenRepository.setAccessToken(accessToken)

    return true;
}


async function api(urlRequest: string, props: PropsType): Promise<any> {
    return new Promise(async function (g_resolve, g_reject) {

        const method = props.method

        //  X_AUTH_TOKEN = "X-Auth-Token";
        //  X_DEVICE_TOKEN = "X-Device-Token";
        //  X_DEVICE_TYPE = "X-Device-Type";
        //  X_DEVICE_WIDTH = "X-Device-Width";
        //  X_APPLICATION_VERSION = "X-Application-Version";
        //  X_LANGUAGE = "X-Language";

        let headers: any = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        if (props.headers != null && Object.keys(props.headers).length > 0) {
            for (const key in props.headers) {
                headers[key] = props.headers[key]
            }
        }

        let init: any = {
            method: method,
            headers: headers,
        }

        if (props.contentJson != null) {
            init['body'] = JSON.stringify(props.contentJson)
        }

        if (props.body != null) {
            init['body'] = props.body
        }

        let fetchRequest = fetch(urlRequest, init);
        let response = await fetchRequest.then();

        debugger
        if (response.status == 401 || response.status == 403) {
            await updateAccessToken()

            fetchRequest = fetch(urlRequest, init);
            response = await fetchRequest.then();
        }

        if(response.ok != true){
            g_reject(response)
            return;
        }

        let jsonResult = await response.json().then()
        g_resolve(jsonResult)

    })
}


async function apiText(urlRequest: string, props: PropsType): Promise<any> {
    return new Promise(async function (g_resolve, g_reject) {

        const method = props.method

        //  X_AUTH_TOKEN = "X-Auth-Token";
        //  X_DEVICE_TOKEN = "X-Device-Token";
        //  X_DEVICE_TYPE = "X-Device-Type";
        //  X_DEVICE_WIDTH = "X-Device-Width";
        //  X_APPLICATION_VERSION = "X-Application-Version";
        //  X_LANGUAGE = "X-Language";

        let headers: any = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        if (props.headers != null && Object.keys(props.headers).length > 0) {
            for (const key in props.headers) {
                headers[key] = props.headers[key]
            }
        }

        let init: any = {
            method: method,
            headers: headers,
        }

        if (props.contentJson != null) {
            init['body'] = JSON.stringify(props.contentJson)
        }

        if (props.body != null) {
            init['body'] = props.body
        }

        let fetchRequest = fetch(urlRequest, init);
        let response = await fetchRequest.then();

        debugger
        if (response.status == 401 || response.status == 403) {
            await updateAccessToken()

            fetchRequest = fetch(urlRequest, init);
            response = await fetchRequest.then();
        }

        if(response.ok != true){
            g_reject(response)
            return;
        }

        let jsonResult = await response.text().then()
        g_resolve(jsonResult)

    })
}

export default {
    api: api,
    apiText: apiText,
    updateAccessToken: updateAccessToken
}