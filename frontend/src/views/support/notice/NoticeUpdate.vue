<template>
    <base-editor
        :submit-url="'/support/notice'"
        :submit-method="'put'"
        :default-nid="id"
        :default-title="title"
        :default-content="content"
        :default-writer="writer"
    />
</template>

<script>
import BaseEditor from '@/components/BaseEditor';

export default {
    name: 'NoticeUpdate',
    components: {
        BaseEditor,
    },
    props: {
        no: {
            type: Number,
            required: true,
        }
    },
    data: () => ({
        id: 0,
        writer: "",
        title:  "",
        content: null,
    }),
    async created() {
        const response = await this.$axios({
            url: 'support/notice/noticeId',
            method: "get",
            params: {
                id: this.no
            }
        })
            .catch((error) => {
                this.$toast('안내', error.response.data.message);
                this.$router.push({ name: 'NoticeList' });
            });

        if (response) {
            if (response.status == 200) {
                this.id = response.data.data.nid;
                this.writer = response.data.data.writer;
                this.writeDate = response.data.data.writeDate;
                this.title = response.data.data.title;
                this.content = response.data.data.content;
            }
        }

    }
};
</script>

<style>

</style>