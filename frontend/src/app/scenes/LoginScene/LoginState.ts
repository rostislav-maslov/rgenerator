interface ViewData{
    email?: string,
    login?: string,
    password?: string,
}

interface LoginState {
    viewData: ViewData,
    apiData: any,
    data: any
}

export default LoginState;
