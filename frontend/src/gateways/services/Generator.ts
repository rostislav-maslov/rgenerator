import * as CONST from './Const'
import API from "./Api";

export default {

    create(title: string, description: string, example: string, accessLevel: string): Promise<any> {
        const urlRequest = CONST.V1 + "/generator"
        const method = 'POST'

        let contentJson = {title, description, example, accessLevel};

        return  API.api(urlRequest, {
            method: method,
            contentJson:contentJson
        });
    },

    updateInfo(id: string, title: string, description: string, accessLevel: string): Promise<any>  {
        const urlRequest = `${CONST.V1}/generator/${id}/info`
        const method = 'POST'

        let contentJson = {title, description, accessLevel};

        return API.api(urlRequest, {
            method: method,
            contentJson:contentJson
        });
    },

    updateJson(id: string, example: string): Promise<any>  {
        const urlRequest = `${CONST.V1}/generator/${id}/json`
        const method = 'POST'

        let contentJson = {example};

        return  API.api(urlRequest, {
            method: method,
            contentJson:contentJson
        });
    },

    findById(id: string): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator/" + id
        const method = 'GET'

        return API.api(urlRequest, {
            method: method,
        });
    },

    deleteById(id: string) : Promise<any> {
        const urlRequest = CONST.V1 + "/generator/" + id
        const method = 'DELETE'

        return API.api(urlRequest, {
            method: method,
        });
    },

    fileContent(id: string, fileId: string): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator/" + id + '/file/' + fileId
        const method = 'GET'

      return  API.api(urlRequest, {
            method: method,
        });
    },

    generateExample(id: string, fileContent: string): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator/" + id + '/generate'
        const method = 'POST'

        let data = new FormData()
        data.append('id', id)
        data.append('content', fileContent)

        let response = API.apiText(urlRequest, {
            method: method,
            body: data
        });

        return response;
    },

    list(): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator"
        const method = 'GET'

       return API.api(urlRequest, {
            method: method,
        });
    },

    myRGenerators(): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator/my"
        const method = 'GET'

        return API.api(urlRequest, {
            method: method,
        });
    },

    uploadFile(id: string, path: string, file: any): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator/file"
        const method = 'POST'

        let headers = {
            'Content-Type': 'multipart/form-data'
        }

        let data = new FormData()
        data.append('id', id)
        data.append('path', path)
        data.append('file', file)

        let props = {
            method: method,
            headers: headers,
            body: data
        }

        let response = API.api(urlRequest, props);

        return response;
    },

    deleteFile(id: string, fileId: string) : Promise<any> {
        const urlRequest = `${CONST.V1}/generator/${id}/file/${fileId}`
        const method = 'DELETE'

        let contentJson = {fileId};

        return API.api(urlRequest, {
            method: method,
            contentJson:contentJson
        });
    },

    generate(id: string, jsonString: string) : Promise<any> {
        const urlRequest = CONST.V1 + "/generator/" + id + "/generate"
        const method = 'POST'

        let data = new FormData()
        data.append('id', id)
        data.append('data', jsonString)

        return API.api(urlRequest, {
            method: method,
            body: data
        });
    },

    sync(id: string, repo: string) : Promise<any> {
        const urlRequest = CONST.V1 + "/generator/" + id + "/sync"
        const method = 'POST'

        let contentJson = {id, repo};

        return API.api(urlRequest, {
            method: method,
            contentJson:contentJson
        });
    },

    check(repo: string): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator/github/check?repo=" + repo
        const method = 'GET'

        return API.api(urlRequest, {
            method: method,
        });
    },

    repos(): Promise<any>  {
        const urlRequest = CONST.V1 + "/generator/github/repos"
        const method = 'GET'

        return API.api(urlRequest, {
            method: method,
        });
    },

}