import * as CONST from './Const'

export interface Developer {
    id: string,
    login: string,
    email: string
}

export default {

    getAccessToken(): string | null{
        return localStorage.getItem("RG_ACCESS_TOKEN")
    },

    setAccessToken(accessToken: string) {
        return localStorage.setItem("RG_ACCESS_TOKEN", accessToken)
    },

    getRefreshToken(): string | null{
        return localStorage.getItem("RG_REFRESH_TOKEN")
    },

    setRefreshToken(refreshToken: string) {
        return localStorage.setItem("RG_REFRESH_TOKEN", refreshToken)
    },

    getCurrentDeveloper(): Developer | null{
        const json:string|null = localStorage.getItem("RG_CURRENT_DEVELOPER")
        if(json == null) return null

        return JSON.parse(json)
    },

    setCurrentDeveloper(developer: Developer){
        return localStorage.setItem("RG_CURRENT_DEVELOPER", JSON.stringify(developer))
    }

}