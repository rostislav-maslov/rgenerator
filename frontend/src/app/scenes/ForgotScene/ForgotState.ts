interface ViewData{
    email?: string,
    login?: string,
    password?: string,
}

interface ForgotState {
    viewData: ViewData,
    apiData: any,
    data: any
}

export default ForgotState;
