import * as CONST from './Const'
import TokenRepository from "./TokenRepository";

export default {

    create(title, description, example) {
        const urlRequest = CONST.V1 + "/generator"
        const method = 'POST'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let contentJson = {title, description, example};

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    updateInfo(id, title, description) {
        const urlRequest = `${CONST.V1}/generator/${id}/info`
        const method = 'POST'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let contentJson = {title, description};

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    updateJson(id, example) {
        const urlRequest = `${CONST.V1}/generator/${id}/json`
        const method = 'POST'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let contentJson = {example};

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    findById(id) {
        const urlRequest = CONST.V1 + "/generator/" + id
        const method = 'GET'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

    deleteById(id) {
        const urlRequest = CONST.V1 + "/generator/" + id
        const method = 'DELETE'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

    fileContent(id, fileId) {
        const urlRequest = CONST.V1 + "/generator/" + id + '/file/' + fileId
        const method = 'GET'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

    generateExample(id, fileContent) {
        const urlRequest = CONST.V1 + "/generator/" + id + '/generate'
        const method = 'POST'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let data = new FormData()
        data.append('id', id)
        data.append('content', fileContent)

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: data
        });

        return response;
    },

    list() {
        const urlRequest = CONST.V1 + "/generator"
        const method = 'GET'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

    myRGenerators() {
        const urlRequest = CONST.V1 + "/generator/my"
        const method = 'GET'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

    uploadFile(id, path, file) {
        const urlRequest = CONST.V1 + "/generator/file"
        const method = 'POST'

        let headers = {}

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let data = new FormData()
        data.append('id', id)
        data.append('path', path)
        data.append('file', file)

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: data
        });

        return response;
    },

    deleteFile(id, fileId) {
        const urlRequest = `${CONST.V1}/generator/${id}/file/${fileId}`
        const method = 'DELETE'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let contentJson = {fileId};

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    generate(id, jsonString) {
        const urlRequest = CONST.V1 + "/generator/" + id + "/generate"
        const method = 'POST'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let data = new FormData()
        data.append('id', id)
        data.append('data', jsonString)

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: data
        });

        return response;
    },

    sync(id, repo) {
        const urlRequest = CONST.V1 + "/generator/" + id + "/sync"
        const method = 'POST'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let contentJson = {id, repo};

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
            body: JSON.stringify(contentJson)
        });

        return response;
    },

    check(repo) {
        const urlRequest = CONST.V1 + "/generator/github/check?repo=" + repo
        const method = 'GET'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

    repos() {
        const urlRequest = CONST.V1 + "/generator/github/repos"
        const method = 'GET'

        let headers = {
            'Content-Type': 'application/json;charset=utf-8'
        }

        const accessToken = TokenRepository.getAccessToken();
        if (accessToken != null) {
            headers['X-Auth-Token'] = TokenRepository.getAccessToken();
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

}