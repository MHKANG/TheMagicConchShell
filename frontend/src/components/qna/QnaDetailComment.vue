<template>
    <div class="comment-wrapper">
        <div class="comment text-left">
            <div class="title-container">
                <div class="buttons">
                    <template v-if="nickname===writer">
                        <template v-if="isQuestion">
                            <div 
                                v-if="nickname"
                                class="button-item cursor-pointer"
                                @click="modifyHandler"
                            >
                                <svg-pencil />
                            </div>
                        </template>
                        <template v-else>
                            <div 
                                v-if="nickname"
                                class="button-item cursor-pointer"
                                @click="changeUpdate"
                            >
                                <svg-pencil />
                            </div>
                        </template>
                        <div
                            v-if="nickname"
                            class="button-item cursor-pointer"
                            :disabled="$wait.is('counsel-chunk')"
                            @click="deleteHandler(no)"
                        >
                            <svg-delete />
                        </div>
                    </template>
                    <template v-else>
                        <div
                            class="button-item cursor-pointer"
                            :disabled="$wait.is('counsel-chunk')"
                            @click="reportHandler"
                        >
                            <svg-exclamation />
                        </div>
                    </template>
                </div>
                <div
                    v-if="isQuestion"
                    class="title-text"
                >
                    {{ title }}
                </div>
            </div>

            <div class="avatar-container">
                <img
                    class="avatar"
                    src="https://img.kbs.co.kr/kbs/620/nsimg.kbs.co.kr/data/news/2010/01/08/2024781_BeB.jpg"
                    onerrer="https://img.kbs.co.kr/kbs/620/nsimg.kbs.co.kr/data/news/2010/01/08/2024781_BeB.jpg"
                    alt="Avatar image"
                >
                <div
                    class="writer"
                >
                    {{ writer }}
                </div>
                <div class="d-flex">
                    <div class="write-date">
                        {{ writeDate }}
                    </div>
                </div>
            </div>

            <div class="comment-body-container">
                <template
                    v-if="isUpdate"
                >
                    <div
                        class="comment-body padding-right-view"
                    >
                        <editor
                            ref="commentUpdateEditor"
                            :options="editorOpts"
                            :initial-value="content"
                            initial-edit-type="wysiwyg"
                        />
                    </div>
                    <div class="comment-body-sidebar">
                        <div 
                            class="btn btn-info comment-update-btn"
                            :disabled="$wait.is('counsel-chunk')"
                            @click="modifyHandlerWrapper"
                        >
                            수정
                        </div>
                    </div>
                </template>
                <template v-else>
                    <div class="comment-body comment-body-view padding-right-view">
                        <viewer
                            :initial-value="content"
                        />
                    </div>
                </template>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import SvgExclamation from '@/components/general/SvgMaterialReportProblem.vue';
import SvgPencil from '@/components/general/SvgMaterialRateReview.vue';
import SvgDelete from '@/components/general/SvgMaterialBackspace.vue';

export default {
    name: "CounselDetailComment",
    components: {
        SvgDelete,
        SvgPencil,
        SvgExclamation,
    },
    props: {
        no: {
            type: Number,
            default: 0,
        },
        writer: {
            type: String,
            required: true,
        },
        writeDate: {
            type: String,
            required: true,
        },
        title: {
            type: String,
            required: false,
            default: '',
        },
        content: {
            type: String,
            required: true,
        },
        isQuestion: {
            type: Boolean,
            default: false,
        },
        deleteHandler: {
            type: Function,
            required: true,
        },
        modifyHandler: {
            type: Function,
            required: true,
        },
        reportHandler: {
            type: Function,
            required: true,
        },
    },
    data(){
        return {
            isUpdate: false,
            editorOpts: null,
        };
    },
    computed: {
        ...mapGetters(['nickname']),
    },
    mounted() {
        this.editorOpts = this.$store.getters.EDITOROPTIONS.options;
    },
    methods: {
        changeUpdate(){
            this.isUpdate = !this.isUpdate;
        },
        modifyHandlerWrapper(){
            const htmlText = this.$refs.commentUpdateEditor.invoke("getHtml");
            this.modifyHandler(htmlText,this.no);
        },
    },
};
</script>

<style lang="scss" scoped>
.hash {
    color: dimgray
}
*[disabled] {
    opacity: 0.4;
}
* {
    box-sizing: border-box;
    display: block;
}
.selected {
    color: green;
}
.inline {
    display: inline;
}
.cursor-default:hover {
    cursor: default;
}
.cursor-pointer:hover {
    cursor: pointer;
}

.wide-only {
    display: none !important;
}

.margin-right-5 {
    margin-right: 5px;
}

.comment-wrapper {
    position: relative;
    display: flex;
    width: 90%;
    left: 0px;
    margin-bottom: 22px;
    min-height: 160px;

    .comment {
        position: relative;
        border: 1px gray solid;
        flex: auto;
        border-radius: 6px;
        max-width: 100%;
    }
}

.title-container {
    padding-left: 16px;
    padding-right: 8px;
    padding-bottom: 3px;
    padding-top: 3px;
    display: flex;
    align-items: center;
    background-color: whitesmoke;
    border-bottom: 1px gainsboro solid;
    border-top-right-radius: 6px;
    border-top-left-radius: 6px;
    flex-direction: row-reverse;
    
    .title-text {
        flex: 1 1 auto;
    }

    .buttons {
        display: flex;
        float: right;
        flex-shrink: 0 !important;
    }
    .button-item {
        margin-left: 4px;
        display: inline-block;
    }
}

.avatar-container {
    z-index: 1;
    display: flex;
    box-sizing: border-box;
    align-items: center;
    border-bottom: 1px dotted lightgray;
    
    .avatar {
        margin: 4px;
        border-radius: 50% !important;
        height: 24px;
        width: 24px;
    }
    .writer {
        flex: 1;
        word-break: break-all;
    }
    .write-date {
        text-align: right;
        flex: 1;
        color: gray;
    }
    
    .button-like {
        color: green;
    }
    .button-dislike {
        color: brown;
    }
}

.comment-body-container {
    display: flex;
    flex-wrap: wrap;
    flex-direction: column;
    align-items: center;

    .comment-body {
        text-align: justify;
        overflow: visible;
        width: 100%;
        word-break: break-all;
    }
    .comment-body-view {
        margin-right: 0px !important;
        padding: 15px;
    }
}

.comment-body-sidebar {
    display: flex;
    flex-direction: column-reverse;
    text-align: center;
    align-items: center;

    .comment-update-btn {
        font-size: 100%;
    }
    .comment-update-secret {
        display: flex;
        align-items: center;
        font-size: 100%;

        .comment-update-secret-checkbox {
            width: auto;
        }
        .comment-update-secret-label {
            margin-bottom: 0.1rem;
            margin-left: 2px;
        }
    }
}

/**
 * Desktop CSS
 */
@media all and (min-width:768px) {
    .content {
        padding-right: 60px;
    }
    .wide-only {
        display: unset !important;
    }
    .mobile-only {
        display: none !important;
    }
    .comment-wrapper {
        width: 75%;
        left: -16px;
    }
    .avatar-container {
        top: 0px;
        max-width: 86px;
        left: -92px;
        position: absolute;
        z-index: 1;
        display: block;
        box-sizing: border-box;
        text-align: center;
        border-bottom: unset;

        .avatar {
            border-radius: 50% !important;
            height: 60px;
            width: 60px;
            margin-bottom: 12px;
        }
        .writer {
            width: 100%;
            word-break: break-all;
        }
        .write-date {
            text-align: unset;
            color: gray;
            font-weight: 100;
        }
    }

    .title-container {
        .button-author {
            display: inline-block;
            border: 1.5px gray solid;
            color: #707070;
            border-radius: 2em;
            padding-top: 0.5px;
            padding-right: 4px;
            padding-left: 5px;
            font-size: 14px;
        }
    }

    .comment-body-container {
        .padding-right-view {
            padding-right: 60px;
        }
        
        .comment-body-sidebar {
            display: flex;
            flex-direction: column-reverse;

            .comment-update-btn{
                position: absolute;
                right: 4px;
                top: 36px;
                font-size: 100%;
                width: unset;
            }
            
            .comment-update-secret{
                position: absolute;
                right: 9px;
                top: 80px;
                display: flex;
                align-items: center;
                font-size: 100%;
                
                .comment-update-secret-checkbox {
                    width: auto;
                }

                .comment-update-secret-label {
                    margin-bottom: 0.1rem;
                    margin-left: 2px;
                }
            }

            .button-like {
                float: right;
                position: absolute;
                right: 16px;
                top: 60px;
                padding-bottom: 2px;
                border-bottom: 1px solid lightgray;
                color: green;
                font-size: 100%;
            }
            .button-dislike {
                float: right;
                position: absolute;
                right: 16px;
                top: 90px;
                color: brown;
                font-size: 100%;
            }
        }
    }
}
</style>