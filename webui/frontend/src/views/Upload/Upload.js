import React, {Component, lazy} from 'react';
import { ToastContainer, toast } from 'react-toastify';
import {Progress} from 'reactstrap';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import {Row,} from 'reactstrap';
import {getStyle} from '@coreui/coreui/dist/js/coreui-utilities'

const Widget03 = lazy(() => import('../../views/Widgets/Widget03'));

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


class Upload extends Component {
    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.onRadioBtnClick = this.onRadioBtnClick.bind(this);

        this.state = {
            dropdownOpen: false,
            radioSelected: 2,
            selectedFile: null,
            loaded:0
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

        return (
            <div className="animated fadeIn">
                <div className="row">

                    <div className="offset-md-3 col-md-6">

                        <div className="form-group files">
                            <label>Upload your files</label>
                            <input type="file" name="file" className="form-control" multiple=""
                                   onChange={this.onChangeHandler}></input>


                        </div>
                        <button type="button" className="btn btn-success btn-block"
                                onClick={this.onClickHandler}>Upload
                        </button>
                    </div>
                </div>

                <div className="form-group">
                    <ToastContainer/>
                    <Progress max="100" color="success" value={this.state.loaded} >{Math.round(this.state.loaded,2) }%</Progress>
                </div>
            </div>
        );
    }

    onChangeHandler = event => {
        this.setState({
            selectedFile: event.target.files,
            loaded: 0,
        });
        console.log(event.target.files[0]);
    }
    onClickHandler = () => {
        const data = new FormData();

        for( var x = 0;x <this.state.selectedFile.length;x++ ) {

            data.append("file", this.state.selectedFile[x]);
            data.append("filename", this.state.selectedFile[x].name);
        }

        let url = "http://" + process.env.REACT_APP_REST_HOST + ":" + process.env.REACT_APP_REST_PORT;

        console.log(url);

        axios.post(url + "/rest/upload", data, {
            onUploadProgress: ProgressEvent => {
                this.setState({
                    loaded: (ProgressEvent.loaded / ProgressEvent.total * 100),
                })
            },
        })
            .then(res => { // then print response status
                console.log('upload success');
                toast.success('upload success');
            })
            .catch(err => { // then print response status
                console.log(err);
                console.log('upload fail');
                toast.error('upload fail');
            });


    }
}

export default Upload;
