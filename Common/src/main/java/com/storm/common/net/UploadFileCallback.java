package com.storm.common.net;


import com.storm.common.entity.DataPart;

import java.util.List;

interface UploadFileCallback {

	void onFailure();

	void onResponse(List<DataPart> dataList);


}
