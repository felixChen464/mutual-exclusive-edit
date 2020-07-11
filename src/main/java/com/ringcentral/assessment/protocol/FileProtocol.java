package com.ringcentral.assessment.protocol;

import lombok.Data;

public interface FileProtocol {

    interface SaveFile{

        @Data
        class Input{

            /**
             * 文件名
             */
            private String fileName;

            /**
             * 文本内容
             */
            private String content;
        }

    }

    interface EditFile{

        @Data
        class Input{

            /**
             * 文件id
             */
            private String fileId;

            /**
             * 文件名
             */
            private String fileName;

            /**
             * 文本内容
             */
            private String content;
        }

    }
}
