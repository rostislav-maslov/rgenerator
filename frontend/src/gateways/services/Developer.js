import * as CONST from './Const'
import TokenRepository from './TokenRepository'

export default {

    me() {
        const urlRequest = `${CONST.V1}/user/me`
        const method = 'GET'

        // public static final String X_AUTH_TOKEN = "X-Auth-Token";
        // public static final String X_DEVICE_TOKEN = "X-Device-Token";
        // public static final String X_DEVICE_TYPE = "X-Device-Type";
        // public static final String X_DEVICE_WIDTH = "X-Device-Width";
        // public static final String X_APPLICATION_VERSION = "X-Application-Version";
        // public static final String X_LANGUAGE = "X-Language";

        let headers = {
            'X-Auth-Token': TokenRepository.getAccessToken(),
            'Content-Type': 'application/json;charset=utf-8'
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            // body: JSON.stringify(contentJson)
        });

        return response;
    },


}