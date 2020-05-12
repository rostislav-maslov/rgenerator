import React from 'react'
import SignUpProps from "./SignUpProps";

import LoginFormComponent from '../../components/loginPage/LoginComponent'
import BannerComponent from "../../components/loginPage/BannerComponent";

const SignUpScene: React.FC<SignUpProps> = (props: SignUpProps) => {
    return (
        <div>
            <BannerComponent/>
           <LoginFormComponent/>
        </div>
    )
}

export default SignUpScene