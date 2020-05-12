import React from 'react'
import LoginProps from "./LoginProps";
import Input from '@material-ui/core/Input';
import Button from '@material-ui/core/Button';

const LoginFormComponent: React.FC<LoginProps> = (props: LoginProps) => {
    return (
        <div>
            <Input type={'text'}/>
            <Input type={'password'}/>
            <Button>SignUp</Button>
            <Button>Login</Button>
            <Button>Forgot password?</Button>
        </div>
    )
}

export default LoginFormComponent