import {makeStyles} from "@material-ui/core/styles";

const LeftMenuStyles = makeStyles((theme) => ({

    container: {
        padding: '0px 24px 24px 24px'
    },
    group: {
        paddingTop: '24px',
        '&::first-of-type': {
            paddingTop: '0px'
        },
    }
}));

export default LeftMenuStyles;