import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({

    container: {
        position: 'relative',
        padding: '24px',
        marginBottom: '16px',
        cursor: 'pointer'
    },

    title: {
        fontWeight: 500,
        fontSize: '28px',
        lineHeight: '34px',
        color: '#000000',
        textDecoration: 'none'
    },

    date: {
        fontSize: '16px',
        lineHeight: '20px',
        color: '#CCCCCC',
        position: 'absolute',
        right: '24px',
        top: '30px'
    },

    description: {
        fontSize: '16px',
        lineHeight: '20px',
        color: '#000000',
    },

}));

export default useStyles;