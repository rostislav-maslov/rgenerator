interface ViewData{
    email?: string,
    login?: string,
    password?: string,
}

interface RecoverState {
    viewData: ViewData,
    apiData: any,
    data: any
}

export default RecoverState;
