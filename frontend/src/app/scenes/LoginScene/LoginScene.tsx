import React from 'react'
import LoginProps from "./LoginProps";

import LoginFormComponent from '../../components/loginPage/LoginComponent'
import BannerComponent from "../../components/loginPage/BannerComponent";
import Grid from '@material-ui/core/Grid';

const LoginScene: React.FC<LoginProps> = (props: LoginProps) => {
    return (
        <section>
            <Grid container spacing={0}
                  direction="row"
                  justify="flex-start"
                  alignItems="flex-start"
            >

                <Grid item xs={12} md={6} lg={6}>
                    <BannerComponent/>
                </Grid>
                <Grid item xs={12} md={6} lg={6}>
                    <LoginFormComponent type={'login'}/>
                </Grid>

            </Grid>


        </section>
    )
}

export default LoginScene