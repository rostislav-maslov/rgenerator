export  default interface LoginProps{
    type: 'login' | 'signup' | 'forgot' | 'recover',

    email?: string,
    login?: string,
    password?: string,

    onSubmit?: (event:any) => void,
    onChange?: (name: 'email'|'login'|'password', value:string) => void
}