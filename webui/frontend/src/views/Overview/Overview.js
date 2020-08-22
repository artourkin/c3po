import React, {Component, lazy} from 'react';
import {
    Badge,
    Button,
    ButtonDropdown,
    ButtonGroup,
    ButtonToolbar,
    Card,
    CardBody,
    CardFooter,
    CardHeader,
    CardTitle,
    Col,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Progress,
    Row,
    Table,
} from 'reactstrap';
import {getStyle} from '@coreui/coreui/dist/js/coreui-utilities'
import Histogram from '../../c3po/Histogram.js'

const Widget03 = lazy(() => import('../Widgets/Widget03'));

const brandPrimary = getStyle('--primary')
const brandSuccess = getStyle('--success')
const brandInfo = getStyle('--info')
const brandWarning = getStyle('--warning')
const brandDanger = getStyle('--danger')

// Main Chart

//Random Numbers
function random(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}


var elements = 27;
var data1 = [];
var data2 = [];
var data3 = [];

for (var i = 0; i <= elements; i++) {
    data1.push(random(50, 200));
    data2.push(random(80, 100));
    data3.push(65);
}

class Overview extends Component {
    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.onRadioBtnClick = this.onRadioBtnClick.bind(this);

        this.state = {
            dropdownOpen: false,
            radioSelected: 2,
        };
    }

    toggle() {
        this.setState({
            dropdownOpen: !this.state.dropdownOpen,
        });
    }

    onRadioBtnClick(radioSelected) {
        this.setState({
            radioSelected: radioSelected,
        });
    }

    loading = () => <div className="animated fadeIn pt-1 text-center">Loading...</div>

    render() {
        const format = 'format';
        const mimetype = 'mimetype';
        return (


            <div className="animated fadeIn">
                <Row>
                    <Col xs="12" sm="6" lg="3">
                        <Card className="text-white">
                            <CardBody className="pb-0">
                            </CardBody>
                            <div className="chart-wrapper">
                                <Histogram prop={format}/>
                            </div>
                        </Card>
                    </Col>


                    <Col xs="12" sm="6" lg="3">
                        <Card className="text-white">
                            <CardBody className="pb-0">
                            </CardBody>
                            <div className="chart-wrapper">
                                <Histogram prop={mimetype}/>
                            </div>
                        </Card>
                    </Col>

                    <Col xs="12" sm="6" lg="3">
                        <Card className="text-white">
                            <CardBody className="pb-0">
                            </CardBody>
                            <div className="chart-wrapper">
                                <Histogram prop="valid"/>
                            </div>
                        </Card>
                    </Col>
                </Row>



            </div>
        );
    }
}

export default Overview;
