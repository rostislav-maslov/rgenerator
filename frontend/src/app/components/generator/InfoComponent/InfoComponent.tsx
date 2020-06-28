import React from 'react'
import InfoProps from "./InfoProps";
import InfoStyles from "./InfoStyles";

import Input from '@material-ui/core/Input'
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';

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
            <FormControl variant="outlined"  required fullWidth margin="normal">
                <InputLabel id="js-generator-access-level">Access</InputLabel>
                <Select
                    labelId="js-generator-access-level"
                    id="js-generator-access-level"
                    value={props.accessLevel}
                    onChange={props.onChangeAccessLevel}
                    label="Access"
                    name={'accessLevel'}
                >
                    <MenuItem value={'PRIVATE'}>Private</MenuItem>
                    <MenuItem value={'PUBLIC'}>Public</MenuItem>
                </Select>
            </FormControl>
        </Paper>
    )
}

export default InfoComponent