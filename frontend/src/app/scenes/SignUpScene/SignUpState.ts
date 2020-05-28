interface ViewData{
    email?: string,
    login?: string,
    password?: string,
}

interface SignUpState {
    viewData: ViewData,
    apiData: any,
    data: any
}

export default SignUpState;
