import React from 'react';
import DividerProps from "./DividerProps";
import Grid from '@material-ui/core/Grid';


interface Props {
}


const DividerComponent: React.FC<DividerProps> = (props: DividerProps) => {

    return (
        <div>
            <br/>

            <Grid container spacing={3}
                  direction="row"
                  justify="flex-start"
                  alignItems="flex-start"
            >
                <Grid item xs={5} md={5} lg={5}>
                    <hr style={{border: '1px solid #aaaaaa'}}/>
                </Grid>
                <Grid item xs={2} md={2} lg={2} style={{textAlign: 'center'}}>
                    {props.text}
                </Grid>
                <Grid item xs={5} md={5} lg={5}>
                    <hr style={{border: '1px solid #aaaaaa'}}/>
                </Grid>
            </Grid>
            <br/>
        </div>
    )
}

export default DividerComponent;

