import {RouteComponentProps} from "react-router";

// Type whatever you expect in 'this.props.match.params.*'
type PathParamsType = {
    code: string,
}


// Your component own properties
type RecoverProps = RouteComponentProps<PathParamsType> & {
}

export default RecoverProps;