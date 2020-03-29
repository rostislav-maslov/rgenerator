import * as CONST from './Const'

export default {

    create(title, description, example) {
        const urlRequest = CONST.V1 + "/generator"
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
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
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
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
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
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
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
        }

        let response = fetch(urlRequest, {
            method: method,
            headers: headers,
        });

        return response;
    },

    list() {
        const urlRequest = CONST.V1 + "/generator"
        const method = 'GET'

        let headers = {
            // Authentication: 'secret',
            'Content-Type': 'application/json;charset=utf-8'
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

        let headers = {
            // Authentication: 'secret',
            // 'Content-Type': 'application/json;charset=utf-8'
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

    generate(id, jsonString){
        const urlRequest = CONST.V1 + "/generator/" + id + "/generate"
        const method = 'POST'

        let headers = {
            // Authentication: 'secret',
            // 'Content-Type': 'application/json;charset=utf-8'
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

}