import {makeStyles} from "@material-ui/core/styles";

const BreadcrumbsItemStyles = makeStyles((theme) => ({

    link: {
        backgroundColor: 'white',
        padding: '7px 16px',
        textDecoration: 'none',
        borderRadius: '16px',
        fontSize: '14px',
        color: '#CFD8DC',
    },

    linkActive: {
        backgroundColor: 'white',
        padding: '7px 16px',
        textDecoration: 'none',
        borderRadius: '16px',
        fontSize: '14px',
        color: '#90A4AE',
    }

}));

export default BreadcrumbsItemStyles;