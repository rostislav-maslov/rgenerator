import {makeStyles} from "@material-ui/core/styles";

const LeftMenuItemStyles = makeStyles((theme) => ({
    container: {
        marginBottom: '8px',
        position: 'relative',
    },
    badge: {
        position: 'absolute',
        right: '0px',
        top: '0px',
        backgroundColor: '#2196F3',
        color: 'white',
        height: '20px',
        borderRadius: '10px',
        minWidth: '20px',
        textAlign: 'center',
        cursor: 'pointer',
    },
    plus: {
        position: 'absolute',
        right: '0px',
        top: '0px',
        backgroundColor: '#4CAF50',
        color: 'white',
        height: '24px',
        borderRadius: '12px',
        width: '24px',
    },
    link: {
        fontSize: '12px',
        lineHeight: '20px',
        color: '#000000',
        textDecoration: 'none',

        '&:hover': {
            color: '#000000',
            textDecoration: 'none',
        },
        '&:active': {
            color: '#000000',
            textDecoration: 'none',
        },
        '&:visited': {
            color: '#000000',
            textDecoration: 'none',
        },
    },
    active: {
        fontWeight: 'bold',
    }

}));

export default LeftMenuItemStyles;