import {RouteComponentProps} from "react-router";

// Type whatever you expect in 'this.props.match.params.*'
type PathParamsType = {
}


// Your component own properties
type PropsType = RouteComponentProps<PathParamsType> & {
}

export default PropsType;