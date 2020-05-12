import React from 'react'
import InfoProps from "./InfoProps";
import InfoStyles from "./InfoStyles";

import Input from '@material-ui/core/Input'
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';

const InfoComponent: React.FC<InfoProps> = (props: InfoProps) => {
    let classes = InfoStyles()

    return (
        <Paper className={classes.paper}>
            <h5 className={classes.title}>Info</h5>
            <TextField label="Title" variant="outlined" required fullWidth margin="normal" value={props.title}
                       name={'title'}
                       onChange={props.onChangeTitle}/>
            <TextField label="Description" variant="outlined" required fullWidth margin="normal"
                       name={'description'}
                       multiline={true}
                       rowsMax={10}
                       rows={4}
                       defaultValue=""
                       value={props.description} onChange={props.onChangeDescription}/>
        </Paper>
    )
}

export default InfoComponent