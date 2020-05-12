import React from 'react'
import LoginProps from "./LoginProps";

import LoginFormComponent from '../../components/loginPage/LoginComponent'
import BannerComponent from "../../components/loginPage/BannerComponent";

const LoginScene: React.FC<LoginProps> = (props: LoginProps) => {
    return (
        <div>
            <BannerComponent/>
           <LoginFormComponent/>
        </div>
    )
}

export default LoginScene