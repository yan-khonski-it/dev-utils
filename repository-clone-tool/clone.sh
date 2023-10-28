# Configure list of repositories to clone
# With this configuration, we will clone
# https://github.com/ByteByteGoHq/system-design-101
# https://github.com/ByteByteGoHq/ml-bytebytego
# into the directory ByteByteGoHq

# https://github.com/yangshun/front-end-interview-handbook
# https://github.com/yangshun/tech-interview-handbook
# into the directory yangshun

# https://github.com/yan-khonski-it/pmd-java-14
# https://github.com/yan-khonski-it/java-17-pmd
# into the directory yan-khonski-it


CURRENT_DIRECTORY="ByteByteGoHq"
mkdir $CURRENT_DIRECTORY

cd $CURRENT_DIRECTORY
git clone https://github.com/ByteByteGoHq/system-design-101
git clone https://github.com/ByteByteGoHq/ml-bytebytego

